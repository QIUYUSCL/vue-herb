package org.example.springboot.entity;

public class HerbConfig {

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Character getConfigType() {
        return configType;
    }

    public void setConfigType(Character configType) {
        this.configType = configType;
    }

    /** 配置 ID，主键，自增 */
    private int configId;
    /** 配置名称 */
    private String configName;
    /** 配置键名，唯一键 */
    private String configKey;
    /** 配置值 */
    private String configValue;
    /** 系统内置，Y 是 N 否，默认值为 N */
    private Character configType;
}
