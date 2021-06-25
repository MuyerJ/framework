package com.muyer.thymeleaf.tag;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring5.context.SpringContextUtils;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Description: 
 * date: 2021/6/25 13:23
 * @author YeJiang
 * @version
 */
public class MyTagSelect extends AbstractElementTagProcessor {

    public MyTagSelect(String dialectPrefix) {
        super(
                // 模板类型为HTML
                TemplateMode.HTML,
                // 标签方言前缀
                dialectPrefix,
                // 标签名称
                "select",
                // 将标签前缀应用于标签名称
                true,
                // 无属性名称：将通过标签名称匹配
                null,
                // 没有要应用于属性名称的前缀
                false,
                // 优先级
                10000
        );
    }

    /**
     * 验证参数是否不为空
     * @param params
     * @return true, 不为空；false，为空
     */
    public static Boolean validParamIsNotNull(String... params) {
        for (String param : params) {
            if (StringUtils.isEmpty(param)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 处理自定义标签 DOM 结构
     *
     * @param iTemplateContext            模板页上下文
     * @param iProcessableElementTag      待处理标签
     *      templateName:template
     *      templateNameMode:HTML
     *      elementCompleteName:FW:select
     *      attributes:标签的属性值map
     * @param iElementTagStructureHandler 元素标签结构处理器
     */
    @Override
    protected void doProcess(ITemplateContext iTemplateContext, IProcessableElementTag iProcessableElementTag, IElementTagStructureHandler iElementTagStructureHandler) {
        // 获取 Spring 上下文
        ApplicationContext applicationContext = SpringContextUtils.getApplicationContext(iTemplateContext);
        // 获取注入bean工厂
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        // 获取所需的bean，一般情况下这里我们直接使用Jdbc来操作数据库，因为它是一个公共组件，数据源不确定，所以要使用动态sql
        JdbcOperations jdbcOperations = autowireCapableBeanFactory.getBean(JdbcOperations.class);
        //select的id属性
        String id = iProcessableElementTag.getAttributeValue("id");
        //select的name属性
        String name = iProcessableElementTag.getAttributeValue("name");
        //option中value的值在数据表中的对应字段
        String colVal = iProcessableElementTag.getAttributeValue("colVal");
        //option中文本的值在数据表中的对应字段
        String colText = iProcessableElementTag.getAttributeValue("colText");
        String fwValue = iProcessableElementTag.getAttributeValue("fwValue");
        //默认值
        String value = iProcessableElementTag.getAttributeValue("value");
        value = StringUtils.isBlank(value) ? "" : value;
        //select的扩展属性
        String otherAttrs = iProcessableElementTag.getAttributeValue("otherAttrs");
        //sql where之后的部分
        String options = iProcessableElementTag.getAttributeValue("options");
        //是否显示请选择
        String selectFlag = iProcessableElementTag.getAttributeValue("selectFlag");
        //表名称
        String tableName = iProcessableElementTag.getAttributeValue("tableName");
        StringBuffer result = new StringBuffer("<select><option value=''>--请选择--</option></select>");
        if (validParamIsNotNull(id, name, colText, colVal, tableName)) {
            //最终拼接的sql语句，这里可以使用jdbc来查询
            String selectSql = "select " + colVal + "," + colText + " from " + tableName + options;
            List<Map<String, Object>> mapList = jdbcOperations.queryForList(selectSql);
            result = new StringBuffer("<select ");
            result.append(" id='").append(id).append("'");
            result.append(" name='").append(name).append("'");
            if (StringUtils.isNotBlank(otherAttrs)) {
                result.append(" ").append(otherAttrs).append(" ");
            }
            result.append(">");
            if (StringUtils.isBlank(selectFlag) || (StringUtils.isNotBlank(selectFlag) && "true".equalsIgnoreCase(selectFlag))) {
                result.append("<option value=''>--请选择--</option>");
            }
            for (Map<String, Object> vo : mapList) {
                String dictValue = Objects.nonNull(vo.get(colVal)) ? vo.get(colVal).toString() : "";
                String dictName = Objects.nonNull(vo.get(colText)) ? vo.get(colVal).toString() : "";
                if (value.equals(dictValue)) {
                    result.append("<option value='").append(dictValue).append("' selected>").append(dictName).append("</option>");
                } else {
                    result.append("<option value='").append(dictValue).append("'>").append(dictName).append("</option>");
                }
            }
            result.append("</select>");
        }
        // 创建将替换自定义标签的 DOM 结构
        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();
        // 这里是将字典的内容拼装成一个下拉框
        model.add(modelFactory.createText(result));
        // 利用引擎替换整合标签
        iElementTagStructureHandler.replaceWith(model, false);
    }

}
