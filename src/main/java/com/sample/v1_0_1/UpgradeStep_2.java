package com.sample.v1_0_1;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradeStep_2 extends UpgradeProcess {

  @Override
  protected void doUpgrade() throws Exception {
    createRole("Role_Two");
  }

  private Role createRole(String name) throws PortalException {
    Role role = RoleLocalServiceUtil.fetchRole(getDefaultCompanyId(), name);
    if (role == null) {
      try {
        role = RoleLocalServiceUtil.addRole(getDefaultAdmin().getUserId(), Role.class.getName(), 0L, name, Map.of(Locale.US, name),
            Map.of(Locale.US, name), RoleConstants.TYPE_REGULAR, "", getServiceContext());
        _log.info("Created role " + name);
      } catch (PortalException e) {
        _log.error("Could not create role " + name);
        throw e;
      }
    } else {
      _log.info("Role " + name + " already exists");
    }
    return role;
  }

  private User getDefaultAdmin() throws PortalException {
    User defaultAdmin;
    String defaultAdminScreenName = PropsUtil.get(PropsKeys.DEFAULT_ADMIN_SCREEN_NAME);

    try {
      defaultAdmin =
          UserLocalServiceUtil.getUserByScreenName(this.getDefaultCompanyId(),
              defaultAdminScreenName);
    } catch (PortalException e) {
      final String msg =
          "PortalException - Unable to get default admin user with screenname ["
              + defaultAdminScreenName + "].";
      _log.error(msg);
      throw e;
    }

    return defaultAdmin;
  }

  private long getDefaultCompanyId() {
    return PortalUtil.getDefaultCompanyId();
  }

  private ServiceContext getServiceContext() {
    ServiceContext serviceContext = new ServiceContext();
    serviceContext.setCompanyId(getDefaultCompanyId());
    return serviceContext;
  }

  private Logger _log = LoggerFactory.getLogger(UpgradeStep_2.class);

}
