package com.sample;

import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.sample.v1_0_0.UpgradeStep_1;
import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class UpgradeRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.0", new UpgradeStep_1());
		// registry.register("1.0.0", "1.0.1", new UpgradeStep_2());
	}

}
