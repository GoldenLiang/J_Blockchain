package com.lc.Block;

import java.util.LinkedList;
import java.util.List;

public class Blockchain {

	private List<Block> blockList;
	
	public Blockchain(List<Block> blockList) {
		this.blockList = blockList;
	}
	
	/**
	 * 添加区块
	 * @param block 区块
	 */
	public void addBlock(Block block) {
		this.blockList.add(block);
	}
	
	/**
	 * 创建区块链
	 * @return
	 */
	public static Blockchain newBlockchain() {
		List<Block> blocks = new LinkedList<>();
		blocks.add(Block.newGenesisBlock());
		return new Blockchain(blocks);
	}

	/**
	 * 添加区块
	 * @param data
	 */
	public void addBlock(String data) {
		Block previousBlock = blockList.get(blockList.size() - 1);
		this.addBlock(Block.newBlock(previousBlock.getHash(), data));
	}

	public List<Block> getBlockList() {
		return blockList;
	}

	public void setBlockList(List<Block> blockList) {
		this.blockList = blockList;
	}
	
}
