/**
 * Created by zhicheng on 14/9/2.
 */

function limitInput($input,min,max){
    var value=$input.val();
    if(parseInt(value) < min ){
        alert('过小');
        $input.val(min);
    }
    if(parseInt(value) > max){
        alert('过大');
        $input.val(max);
    }
 }
