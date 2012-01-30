package tools;

import models.fault.SysConstant;
import play.mvc.Scope;

import java.util.List;

/**
 * Desc  :
 * -----
 * Author: <a href="mailto:ember319@gmail.com">ember</a>
 * Date  : 12-1-28
 * Time  : 上午12:22
 */
public class ConstSelectInputTool {
    
    
    public static ConstSelectValue parse(
            String name,Scope.Params params,List<SysConstant> constants){

        ConstSelectValue value=new ConstSelectValue();
        value.constType=Long.parseLong(params.get(name+"ConstType"));
        value.value=params.get(name);
        value.input=params.get(name+"Input");

        if(value.value!=null && !value.value.trim().equals("")){
            for(SysConstant cons:constants){
                if(cons.constType!=0 
                        && cons.constType.equals(value.constType)
                        && Long.parseLong(value.value)==cons.constCode){
                    value.selectedConst=cons;
                    break;
                }
            }
        }
        return value;
    }



    public static ConstSelectValue parseOrSave(String name,Scope.Params params,List<SysConstant> constants){
        ConstSelectValue csValue=parse(name,params,constants);
        if(csValue.selectedConst==null){
            //todo: save as new constant, how to deal with concurrent case?
        }
        return csValue;
    }
    
    
    public static class ConstSelectValue{
        public String value;//下拉菜单选择值
        public String input;//输入框值
        public Long constType;//下来菜单所属常量类型ID
        public SysConstant selectedConst;//选中的常量

        public ConstSelectValue() {
        }

        public ConstSelectValue(String value, String input, SysConstant selectedConst,Long constType) {
            this.value = value;
            this.input = input;
            this.selectedConst = selectedConst;
            this.constType=constType;
        }

        @Override
        public String toString() {
            return "ConstSelectValue{" +
                    "value='" + value + '\'' +
                    ", input='" + input + '\'' +
                    ", constType=" + constType +
                    ", selectedConst=" + selectedConst +
                    '}';
        }
    }
}
