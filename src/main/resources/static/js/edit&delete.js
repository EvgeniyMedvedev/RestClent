$(function () {
    $.fn.serializeFormJSON = function () {

        const o = {};
        const a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);
function editById(id,form) {
    let editUser = {
        id:$('#idT'),
        login:('#loginT'),
        name:('#nameT'),
        password:('#passwordT'),
    };
    let $form = $(form);
    $.ajax({
        type: $form.attr('method'),  // метод который ты будешь использовать для отправки, можно написать текстом, а можно взять с формы...так как у нашей формы есть аттрибут method то я беру это с формы
        url: $form.attr('action'), // url на который ты будешь отправлять данные, можно написать текстом, а можно взять с формы...так как у нашей формы есть аттрибут action то я беру это с формы
        data:editUser,//serializeFormJSON(),  // сами данные это либо string либо array, можно написать ручками но беру данные с форм
        success: function(){  // функция вызовится если все пройдет хорошо и данные спокойно уйдут по url
            reset();
            getTable();
            alert('№ ' + id +' - edited');
        },
        error: function() { // функция вызовится если все пройдет плохо
            alert('Данные не отправлены')
        }
    });

    return false;
}
function deleteById(id) {
        deleteUser();
        
        function deleteUser(){

            $.ajax({
                type:'DELETE',
                url: id + '/delete',
                success:function () {
                    alert('user №' + id + ' - deleted');
                    reset();
                    getTable();
                },
                error : function(e) {
                    $(".table").append("<strong>Error"+ e +"</strong>");
                    reset();
                    getTable();
                }
            })
        }
}