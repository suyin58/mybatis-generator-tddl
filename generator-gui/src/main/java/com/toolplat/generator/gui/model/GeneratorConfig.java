package com.toolplat.generator.gui.model;

import javafx.collections.ObservableList;
import lombok.Data;

/**
 *
 * GeneratorConfig is the Config of mybatis generator config exclude database
 * config
 *
 * Created by Owen on 6/16/16.
 */
@Data
public class GeneratorConfig {

	/**
	 * 本配置的名称
	 */
	private String name;

	private String connectorJarPath;

	private String projectFolder;

	private String modelPackage;

	private String modelPackageTargetFolder;

	private String daoPackage;

	private String daoTargetFolder;

	private String mapperName;

	private String mappingXMLPackage;

	private String mappingXMLTargetFolder;

	private String tableName;

	private String domainObjectName;

	private boolean offsetLimit;

	private boolean comment;

	private boolean overrideXML;


	private boolean useLombokPlugin;

	private boolean needForUpdate;

	private boolean useExample;

	private String generateKeys;

	private String encoding;

	private boolean useSchemaPrefix;


	private boolean useUniqueKey;
	private String uniqueKeyName;
	private boolean usePrimaryKey;

	private ObservableList observableList;




}
