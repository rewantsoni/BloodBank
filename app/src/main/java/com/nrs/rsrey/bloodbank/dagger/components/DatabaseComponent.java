package com.nrs.rsrey.bloodbank.dagger.components;

import com.nrs.rsrey.bloodbank.dagger.modules.DatabaseModule;
import com.nrs.rsrey.bloodbank.dagger.scopes.ApplicationScope;
import com.nrs.rsrey.bloodbank.utils.DbUtil;

import dagger.Component;

@ApplicationScope
@Component(modules = {DatabaseModule.class})
public interface DatabaseComponent {
    DbUtil getDbUtil();
}
