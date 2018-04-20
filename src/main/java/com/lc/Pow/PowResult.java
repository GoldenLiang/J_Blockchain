package com.lc.Pow;

import lombok.Data;

/**
 * @author lc
 * 工作量计算结果
 */
@Data
public class PowResult {

	/**
	 * 计数器
	 */
	private long nonce;   
	
	/**
	 * hash 值
	 */
	private String hash;
	
	public PowResult(long nonce, String hash) {
		this.nonce = nonce;
		this.hash = hash;
	}

	public long getNonce() {
		return nonce;
	}

	public String getHash() {
		return hash;
	}

	public void setNonce(long nonce) {
		this.nonce = nonce;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
}
