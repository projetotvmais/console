var Currency = {
    addListener: function (element){
        var matcher = "0123456789";
        var id = "#"+element.id;
        $(id).keypress(function(event){
            event.preventDefault();
        });
        $(id).keydown(function(event){
            event.preventDefault();
        });
        $(id).keyup(function(event){
            event.preventDefault();
            var key = event.key;
            var keycode = event.keyCode;
            var text = "" + $(id).val();

            if(matcher.indexOf(""+key) > -1){
                text += key;
                text = text.replace(".","");
                text = convertToMoney(text);
                $(id).val(text);
            }
            else{
                if(key == "Backspace" || keycode == 8){
                    text = text.substr(0,text.length - 1);
                    text = text.replace(".","");
                    text = convertToMoney(text);
                    $(id).val(text);
                }
                console.log("Field " + id + ": Key "+key+" (code = "+keycode+") pressed. (NaN)");
            }
        });
    }
}

function convertToMoney(value){
    var aux = value;
    // remove zeros a esquerda
    while(aux.indexOf("0") == 0){
        aux = aux.substr(1);
    }
    if(aux.length == 0){
        return "0.00";
    }
    if(aux.length == 1){
        return "0.0"+aux;
    }
    else if(aux.length == 2){
        return "0."+aux;
    }
    else{
        var fraction = aux.substr(aux.length - 2);
        var integer = aux.substr(0,aux.length - 2);
        var novo = integer + "." + fraction;
        return novo;
    }
}

$(function(){
    // Adiciona listener em campos currency
    var currencyFields = $(".currency");
    var i = 0;
    for(i = 0;i < currencyFields.length;i++){
        Currency.addListener(currencyFields[i]);
    }
});