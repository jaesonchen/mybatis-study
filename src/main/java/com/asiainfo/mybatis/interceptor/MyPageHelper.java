package com.asiainfo.mybatis.interceptor;

/**   
 * @Description: TODO
 * 
 * @author chenzq  
 * @date 2019年5月11日 下午9:26:11
 * @version V1.0
 * @Copyright: Copyright(c) 2019 jaesonchen.com Inc. All rights reserved. 
 */
public class MyPageHelper {

    public static final ThreadLocal<MyPageParameter> param = new ThreadLocal<>();
    
    public static void startPage(int pageNum) {
        param.set(new MyPageParameter(pageNum));
    }
    
    public static void startPage(int pageNum, int pageSize) {
        param.set(new MyPageParameter(pageNum, pageSize));
    }
    
    public static void stopPage() {
        param.set(null);
    }
    
    public static boolean isInPage() {
        return param.get() != null;
    }
    
    public static String getPageSql(String sql, String type, int defaultPageSize) {
        
        MyPageParameter parameter = param.get();
        if (null != parameter) {
            int pageSize = parameter.getPageSize() > 0 ? parameter.getPageSize() : defaultPageSize;
            if ("mysql".equals(type)) {
                int start = parameter.getPageNum() <= 0 ? 0 : (parameter.getPageNum() - 1) * pageSize;
                return sql.trim() + " limit " + start + ", " + pageSize;
            } else if ("oracle".equals(type)) {
                int start = parameter.getPageNum() <= 0 ? 1 : ((parameter.getPageNum() - 1) * pageSize + 1);
                int end = parameter.getPageNum() <= 0 ? pageSize : parameter.getPageNum() * pageSize;
                StringBuilder sb = new StringBuilder();
                sb.append("  select * ")
                    .append("from (select a.*, rownum rn from (").append(sql.trim()).append(") a ")
                    .append("      where rownum<=").append(end).append(") ")
                    .append("where rn>=").append(start);
                return sb.toString();
            }
        }
        return sql;
    }
}
