/*
 *
 * Copyright 2017-2018 549477611@qq.com(xiaoyu)
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.myth.core.helper;

import com.github.myth.common.utils.DbTypeUtils;

/**
 * SqlHelper.
 * @author xiaoyu
 */
public class SqlHelper {

    /**
     *  build create table sql.
     * @param driverClassName db driver.
     * @param tableName table name.
     * @return sql
     */
    public static String buildCreateTableSql(final String driverClassName, final String tableName) {
        StringBuilder createTableSql = new StringBuilder();
        String dbType = DbTypeUtils.buildByDriverClassName(driverClassName);
        switch (dbType) {
            case "mysql":
                createTableSql.append("CREATE TABLE `")
                        .append(tableName).append("` (\n")
                        .append("  `trans_id` varchar(64) NOT NULL COMMENT '主键,唯一索引',\n")
                        .append("  `target_class` varchar(256) COMMENT '目标CLASS' ,\n")
                        .append("  `target_method` varchar(128) COMMENT '目标method' ,\n")
                        .append("  `retried_count` int(3) NOT NULL COMMENT '重试次数',\n")
                        .append("  `create_time` datetime NOT NULL COMMENT '创建时间',\n")
                        .append("  `last_time` datetime NOT NULL COMMENT '结束时间',\n")
                        .append("  `version` int(6) NOT NULL COMMENT '版本号',\n")
                        .append("  `status` int(2) NOT NULL COMMENT '状态码:0是回滚,1是已经提交,2是开始,3是可以发送消息,4是失败,5是预提交,6是锁定',\n")
                        .append("  `invocation` longblob,\n")
                        .append("  `role` int(2) NOT NULL COMMENT '角色码:1是发起者,2是本地执行,3是提供者',\n")
                        .append("  `error_msg` text COMMENT '错误信息,可以为空',\n")
                        .append("   PRIMARY KEY (`trans_id`),\n")
                        .append("   KEY  `status_last_time` (`last_time`,`status`) USING BTREE \n")
                        .append(")");
                break;

            case "oracle":
                createTableSql
                        .append("CREATE TABLE `")
                        .append(tableName)
                        .append("` (\n")
                        .append("  `trans_id` varchar(64) NOT NULL,\n")
                        .append("  `target_class` varchar(256) ,\n")
                        .append("  `target_method` varchar(128) ,\n")
                        .append("  `retried_count` int(3) NOT NULL,\n")
                        .append("  `create_time` date NOT NULL,\n")
                        .append("  `last_time` date NOT NULL,\n")
                        .append("  `version` int(6) NOT NULL,\n")
                        .append("  `status` int(2) NOT NULL,\n")
                        .append("  `invocation` BLOB ,\n")
                        .append("  `role` int(2) NOT NULL,\n")
                        .append("  `error_msg` CLOB  ,\n")
                        .append("  PRIMARY KEY (`trans_id`)\n")
                        .append(")");
                break;
            case "sqlserver":
                createTableSql
                        .append("CREATE TABLE `")
                        .append(tableName)
                        .append("` (\n")
                        .append("  `trans_id` varchar(64) NOT NULL,\n")
                        .append("  `target_class` varchar(256) ,\n")
                        .append("  `target_method` varchar(128) ,\n")
                        .append("  `retried_count` int(3) NOT NULL,\n")
                        .append("  `create_time` datetime NOT NULL,\n")
                        .append("  `last_time` datetime NOT NULL,\n")
                        .append("  `version` int(6) NOT NULL,\n")
                        .append("  `status` int(2) NOT NULL,\n")
                        .append("  `invocation` varbinary ,\n")
                        .append("  `role` int(2) NOT NULL,\n")
                        .append("  `error_msg` varchar(8000) ,\n")
                        .append("  PRIMARY KEY (`trans_id`)\n")
                        .append(")");
                break;
            default:
                throw new RuntimeException("dbType类型不支持,目前仅支持mysql oracle sqlserver.");
        }
        return createTableSql.toString();
    }

}
