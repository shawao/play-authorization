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
public class ConstSelectInputReader {
    
    
    public static ConstSelectValue parse(
            String name,Long constType,
            Scope.Params params,List<SysConstant> constants){
        ConstSelectValue value=new ConstSelectValue();
        value.value=params.get(name);
        value.input=params.get(name+"Input");

        if(value.value!=null && !value.value.trim().equals("")){
            for(SysConstant cons:constants){
                if(cons.constType!=0 
                        && cons.constType.equals(constType)
                        && Long.parseLong(value.value)==cons.constCode){
                    value.selectedConst=cons;
                    break;
                }
            }
        }
        return value;
    }
    
    
    public static class ConstSelectValue{
        public String value;
        public String input;
        public SysConstant selectedConst;

        public ConstSelectValue() {
        }

        public ConstSelectValue(String value, String input, SysConstant selectedConst) {
            this.value = value;
            this.input = input;
            this.selectedConst = selectedConst;
        }

        @Override
        public String toString() {
            return "ConstSelectValue{" +
                    "value='" + value + '\'' +
                    ", input='" + input + '\'' +
                    ", selectedConst=" + selectedConst +
                    '}';
        }
    }
}
