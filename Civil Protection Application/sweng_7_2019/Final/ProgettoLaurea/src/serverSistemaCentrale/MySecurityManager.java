package serverSistemaCentrale;

import java.security.Permission;

public class MySecurityManager extends SecurityManager {
	@Override
	public void checkPermission(Permission perm) {
		return;
	}
}