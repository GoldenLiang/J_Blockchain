package com.lc.Pow;

import java.math.BigInteger;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.lc.Block.Block;
import com.lc.Util.ByteUtils;

import lombok.Data;

@Data
public class ProofOfWork {

	/**
	 * 难度目标位
	 */
	public static final int TARGET_BITS = 20;
	
	/**
	 * 区块
	 */
	private Block block;
	
	/**
	 * 难度目标值
	 */
	private BigInteger target;
	
	private ProofOfWork(Block block, BigInteger target) {
		this.block = block;
		this.target = target;
	}
	
	/**
	 * 创建新的工作量证明，设定难度目标值
	 * @param block
	 * @return
	 */
	public static ProofOfWork newProofOfWork(Block block) {
		BigInteger targetValue = BigInteger.valueOf(1).shiftLeft(256 - TARGET_BITS);
		return new ProofOfWork(block, targetValue);
	}
	
	/**
	 * 准备数据
	 * @param nonce
	 * @return
	 */
	private byte[] prepareData(long nonce) {
		byte[] prevBlockHashBytes = {};
		if(StringUtils.isNoneBlank(this.getBlock().getPreviousHash())) {
			prevBlockHashBytes = new BigInteger(this.getBlock().getPreviousHash(), 16).toByteArray();
		}
		
		return ByteUtils.merge(
			prevBlockHashBytes,
			this.getBlock().getData().getBytes(),
			ByteUtils.toBytes(this.getBlock().getTimeStamp()),
			ByteUtils.toBytes(TARGET_BITS),
			ByteUtils.toBytes(nonce)
		);
	}

	/**
	 * 运行工作量证明，开始挖矿，找到小于难度目标值的 hash
	 * @return
	 */
	public PowResult run() {
		long nonce = 0;
		String shaHex = "";
		System.out.printf("Mining the block containing: %s \n",  this.getBlock().getData());
		long startTime = System.currentTimeMillis();
		while(nonce < Long.MAX_VALUE) {
			byte[] data = this.prepareData(nonce);
			shaHex = DigestUtils.sha256Hex(data);
			if(new BigInteger(shaHex, 16).compareTo(this.target) == -1) {
				System.out.printf("Elapsed Time: %s seconds \n", (float) (System.currentTimeMillis() - startTime) / 1000);
                System.out.printf("correct hash Hex: %s \n\n", shaHex);
                break;
			} else {
				nonce++;
			}
		}
		return new PowResult(nonce, shaHex);
	}
	
	public boolean validate() {
		byte[] data = this.prepareData(this.getBlock().getNonce());
		return new BigInteger(DigestUtils.sha256Hex(data), 16).compareTo(this.target) == -1;
	}
	public Block getBlock() {
		return block;
	}

	public BigInteger getTarget() {
		return target;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public void setTarget(BigInteger target) {
		this.target = target;
	}
	
}
