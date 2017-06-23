package com.vod.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class GeneratingDbService
{
    private String packageName = "com.vod.db";
    private String modeSrc = "database";
    private String serviceSrc = "src";
    private String dir;
    private String modelDir;
    StringBuilder sb = null;

    
    public void startCreat() throws Exception
    {
        init();
        Set<String> set = listClassName(modelDir, "Example", false);
        for (String className : set)
        {
            autoProdutDbServer(className);
        }
    }
    
    public void startCreat(String gclassName) throws Exception
    {
        init();
        Set<String> set = listClassName(modelDir, "Example", false);
        for (String className : set)
        {
//            System.out.println(className);
            if(className.equals(gclassName)){
                autoProdutDbServer(className);
            }
        }
    }

    private void init()
    {
        this.dir = System.getProperty("user.dir") + File.separator + serviceSrc + File.separator;
        String[] array = packageName.split("\\.");
        sb = new StringBuilder();
        for (String str : array)
        {
            sb.append(str).append(File.separator);
        }
        modelDir = System.getProperty("user.dir") + File.separator + modeSrc + File.separator + sb.toString() + "model";
    }

    private Set<String> listClassName(String fileDir, String str, boolean flag)
    {
        System.out.println(fileDir);
        Set<String> set = new HashSet<String>();
        File file = new File(fileDir);
        if (!file.exists())
        {
            file.mkdirs();
        }
        File[] files = file.listFiles();
        if (null != files && files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isFile())
                {
                    String fileName = files[i].getName();
                    boolean b = fileName.indexOf(str) != -1;
                    if (b == flag)
                    {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        String serviceFile = System.getProperty("user.dir") + File.separator + serviceSrc
                                + File.separator + sb.toString() + "service" + File.separator + "Db" + className
                                + "Service.java";
                        String serviceImplFile = System.getProperty("user.dir") + File.separator + serviceSrc
                                + File.separator + sb.toString() + "service" + File.separator + "impl" + File.separator
                                + "Db" + className + "ServiceImpl.java";
                        File f = new File(serviceFile);
                        
                        if (!f.exists())
                        {
                            set.add(className);
                            continue;
                        } 

                        f = new File(serviceImplFile);
                        if (!f.exists())
                        {
                            set.add(className);
                        }
                    }
                }
            }
        }
        return set;
    }

    private void autoProdutDbServer(String className) throws Exception
    {
        PrintStream ps = null;
        String fileName = null;
        File file = null;

        // EntityService
        fileName = dir + sb.toString() + "service" + File.separator + "Db" + className + "Service.java";
        file = new File(fileName);
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        ps = new PrintStream(new FileOutputStream(file));

        ps.println("package " + packageName + ".service;");
        ps.println();
        ps.println("import " + packageName + ".model." + className + ";");
        ps.println();
        ps.println("public interface " + "Db" + className + "Service" + " extends" + " BaseDbService<" + className
                + ">");
        ps.println("{");
        ps.println();
        ps.println("}");
        ps.close();

        // Audit DbServiceImpl
        fileName = dir + sb.toString() + "service" + File.separator + "impl" + File.separator + "Db" + className
                + "ServiceImpl.java";
        file = new File(fileName);

        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        ps = new PrintStream(new FileOutputStream(file));
        ps.println("package " + packageName + ".service.impl;");
        ps.println();
        ps.println("import java.util.List;");
        ps.println("import " + packageName + ".model." + className + ";");
        ps.println("import " + packageName + ".model." + className + "Example;");
        ps.println("import " + packageName + ".mapper." + className + "Mapper;");
        ps.println("import com.vod.util.Response;");
        ps.println("import " + packageName + ".service." + "Db" + className + "Service;");
        ps.println();
        ps.println("public class " + "Db" + className + "ServiceImpl" + " extends" + " BaseDbServiceImpl<" + className
                + ">" + " implements " + "Db" + className + "Service");
        ps.println("{");

        String mapperName = (className.substring(0, 1)).toLowerCase() + className.substring(1) + "Mapper";

        ps.println("	private " + className + "Mapper " + mapperName + ";");
        ps.println();

        // public DbResponse<Long> count()
        ps.println("	@Override");
        ps.println("	public Response<Integer> count()");
        ps.println("	{");
        ps.println("		long total = 0;");
        ps.println("		total = " + mapperName + ".countByExample(null);");
        ps.println("		Response<Integer> dbResponse = new Response<Integer>();");
        ps.println("		dbResponse.setData(total);");
        ps.println("        return dbResponse;");
        ps.println("    }");
        ps.println();

        // public DbResponse<Audit> getById(Long id)
        ps.println("    @Override");
        ps.println("    public Response<" + className + "> getById(Integer id)");
        ps.println("    {");
        ps.println("        Response<" + className + "> dbResponse = new Response<" + className + ">();");
        ps.println("        dbResponse.setData(" + mapperName + ".selectByPrimaryKey(id));");
        ps.println("        return dbResponse;");
        ps.println("    }");
        ps.println();

        // public List<Audit> list(Integer offset, Integer limit)
        ps.println("    @Override");
        ps.println("    public Response<List<" + className + ">> list(Integer offset, Integer limit)");
        ps.println("    {");
        ps.println("        Response<List<" + className + ">> dbResponse = new Response<List<" + className
                + ">>();");
        ps.println("        " + className + "Example example = null;");
        ps.println("        if (offset != null || limit != null)");
        ps.println("        {");
        ps.println("            example = new " + className + "Example();");
        ps.println("            example.setLimit(limit);");
        ps.println("            example.setOffset(offset);");
        ps.println("        }");
        ps.println("        dbResponse.setData(" + mapperName + ".selectByExample(example));");

        ps.println("        return dbResponse;");
        ps.println("    }");
        ps.println();

        // protected int deleteById(Long id)
        ps.println("    @Override");
        ps.println("    protected int deleteById(Integer id)");
        ps.println("    {");
        ps.println("        return " + mapperName + ".deleteByPrimaryKey(id);");
        ps.println("    }");
        ps.println();

        // protected int update(Audit record)
        ps.println("    @Override");
        ps.println("    protected int update(" + className + " record)");
        ps.println("    {");
        ps.println("        return " + mapperName + ".updateByPrimaryKey(record);");
        ps.println("    }");
        ps.println();

        // protected int insert(Audit record)
        ps.println("    @Override");
        ps.println("    protected int insert(" + className + " record)");
        ps.println("    {");
        ps.println("        return " + mapperName + ".insert(record);");
        ps.println("    }");
        ps.println();

        // public void setAuditMapper(AuditMapper auditMapper)
        ps.println("    public void set" + className + "Mapper(" + className + "Mapper " + mapperName + ")");
        ps.println("    {");
        ps.println("        this." + mapperName + " = " + mapperName + ";");
        ps.println("    }");
        ps.println();

        ps.println("}");
        ps.close();
    }

    public static void main(String[] args) throws Exception
    {
        new GeneratingDbService().startCreat("UserInfoLog");
    }

}
