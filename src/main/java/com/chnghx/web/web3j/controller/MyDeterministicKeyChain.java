package com.chnghx.web.web3j.controller;

import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

public class MyDeterministicKeyChain extends DeterministicKeyChain{

	public MyDeterministicKeyChain(DeterministicSeed seed) {
		super(seed);
	}

}
