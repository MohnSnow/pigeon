/**
 * Dianping.com Inc.
 * Copyright (c) 2003-2013 All Rights Reserved.
 */
package com.dianping.pigeon.remoting.invoker.route.balance;

import java.util.List;

import com.dianping.pigeon.component.invocation.InvocationRequest;
import com.dianping.pigeon.remoting.invoker.Client;

/**
 * 随即负载均衡策略
 * 
 * @author jianhuihuang
 * @version $Id: RandomLoadBalance.java, v 0.1 2013-7-5 上午8:30:36 jianhuihuang
 *          Exp $
 */
public class RandomLoadBalance extends AbstractLoadBalance {

	// private static final Logger logger =
	// Logger.getLogger(RandomLoadBalance.class);

	public static final String NAME = "random";

	public static final LoadBalance instance = new RandomLoadBalance();

	@Override
	public Client doSelect(List<Client> clients, InvocationRequest request, int[] weights) {

		int clientSize = clients.size();
		int totalWeight = 0;
		boolean weightAllSame = true;
		for (int i = 0; i < clientSize; i++) {
			totalWeight += weights[i];
			if (weightAllSame && i > 0 && weights[i] != weights[i - 1]) {
				weightAllSame = false;
			}
		}
		if (!weightAllSame) {
			int weightPoint = random.nextInt(totalWeight);
			for (int i = 0; i < clientSize; i++) {
				Client client = clients.get(i);
				weightPoint -= weights[i];
				if (weightPoint < 0) {
					return client;
				}
			}
		}
		return clients.get(random.nextInt(clientSize));
	}

}