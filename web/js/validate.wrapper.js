/**
 * @requires jquery.validate.js
 */
function ValidatorWrapper(form) {
	var errMsg = "";
    var showErrorMessage = true;
    var validator = form.validate({
        errorPlacement: function (error, element) {
            if (showErrorMessage) {
                var tagName = $(element).parents("p").find("label:eq(0)").text();
                var msg = error.html();
                errMsg += tagName + ": " + msg + "<br/>";
            }
        },
        showErrors: function (errorMap, errorList) {
            this.defaultShowErrors();
            if ((errorList.length != 0) && showErrorMessage) {
                art.dialog.alert(errMsg);
                errMsg="";
            }
        },
        onkeyup: function(element){},
        onfocusin: function(element){},
        onclick: function(element){},
        onfocusout: function(element){}
    });
 
    // This is the function to call whem make the validation
    this.validate = function () {
        var result = validator.form();
        return result;
    };
}