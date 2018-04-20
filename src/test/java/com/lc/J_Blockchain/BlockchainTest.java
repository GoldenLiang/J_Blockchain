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
		blockchain.addBlock("Send 1 BTC to Ivan");
		blockchain.addBlock("Send 2 BTC to Ivan");
		
		for(Block block : blockchain.getBlockList()) {
			System.out.println("Prev.hash: " + block.getPreviousHash());
			System.out.println("Data: " + block.getData());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Nonce: " + block.getNonce());
            
            ProofOfWork pow = ProofOfWork.newProofOfWork(block);
            System.out.println("Pow valid: " + pow.validate() + "\n");
		}
	}
}
