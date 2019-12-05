$(function () {
    $('#creatUser').submit(function(event) {
            // Предотвращаем обычную отправку формы
            event.preventDefault();
            addUser();

        });

    function addUser() {
        const user = {
            name:$('#creatUser > #name').val(),
            login:$('#creatUser > #login').val(),
            password:$('#creatUser > #password').val()
        };
        $.ajax({
            type:'POST',
            contentType : "application/json",
            url:$('#creatUser').attr('action'),
            data:JSON.stringify(user),
            dataType : 'json',
            success:function () {
                reset();
                getTable();
                alert(user.name + ' - added');
            },
            error : function(e) {
                $("#creatUser").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
        resetData();
    }

    function resetData(){
        $("#creatUser > #name").val("");
        $("#creatUser > #login").val("");
        $("#creatUser > #password").val("");
    }

});