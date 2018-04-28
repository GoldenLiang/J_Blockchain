package com.lc.J_Blockchain;

import com.lc.Block.Block;
import com.lc.Block.Blockchain;
import com.lc.Pow.ProofOfWork;

/**
 * @author lc
 * 测试类
 */
public class BlockchainTest {

	public static void main(String[] args) {
		Blockchain blockchain = Blockchain.newBlockchain();
		try {
			blockchain.addBlock("Send 1 BTC to Ivan");
			blockchain.addBlock("Send 2 BTC to Ivan");
			
			for(Blockchain.BlockchainIterator iterator = blockchain.getBlockchainIterator(); 
				iterator.hashNext();) {
				Block block = iterator.next();
				
				if(block != null) {
					boolean validate = ProofOfWork.newProofOfWork(block).validate();
	                System.out.println(block.toString() + ", validate = " + validate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
