package org.mybatis.generator.plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class InsertBatchPlugin extends PluginAdapter
{

    /**
     * {@inheritDoc}
     */
    public boolean validate(List<String> warnings)
    {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable)
    {

        interfaze.addMethod(insertBatch(method, introspectedTable));

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable)
    {

        interfaze.addMethod(insertBatch(method, introspectedTable));

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable)
    {

        topLevelClass.addMethod(insertBatch(method, introspectedTable));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable)
    {

        topLevelClass.addMethod(insertBatch(method, introspectedTable));
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable)
    {

        XmlElement parentElement = document.getRootElement();

        // 产生分页语句前半部分
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", "insertBatch"));
        answer.addAttribute(new Attribute("parameterType", "java.util.List"));

        context.getCommentGenerator().addComment(answer);

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" ("); //$NON-NLS-1$

        valuesClause.append("values <foreach item=\"item\" index=\"index\" collection=\"records\"  separator=\",\" > ("); //$NON-NLS-1$

        List<String> valuesClauses = new ArrayList<String>();
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext())
        {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.isIdentity())
            {
                // cannot set values on identity fields
                continue;
            }

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
            if (iter.hasNext())
            {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
            }

            if (valuesClause.length() > 80)
            {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }
        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(")</foreach>");
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses)
        {
            answer.addElement(new TextElement(clause));
        }

        if (context.getPlugins().sqlMapInsertElementGenerated(answer, introspectedTable))
        {
            parentElement.addElement(answer);
        }

        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private Method insertBatch(Method method, IntrospectedTable introspectedTable)
    {

        Method m = new Method("insertBatch");

        m.setVisibility(method.getVisibility());
        m.setReturnType(FullyQualifiedJavaType.getIntInstance());
        
        String type = introspectedTable.getBaseRecordType();
        FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewListInstance();
        param.addTypeArgument(new FullyQualifiedJavaType(type));

        m.addParameter(new Parameter(param, "records", "@Param(\"records\")"));

        context.getCommentGenerator().addGeneralMethodComment(m, introspectedTable);
        return m;
    }

}