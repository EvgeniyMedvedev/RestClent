$(function () {
    getTable();
});
const html = $('.table');

function getTable() {
    $.getJSON('/users', function (users) {
        users.forEach(function (user) {

            let userElement = user['id'];

            html.append(`
                <tr>
                   <th scope='row'> ${user['id']}  </th>
                   <td> ${user['name']} </td>
                   <td> ${user['login']} </td>
                   <td> ${user['password']} </td>
                   <td> ${user['roles'][0]['name']} </td>
                   <td>
                           <button onclick='deleteById(${userElement})' class='btn btn-primary'>Удалить</button>
                   </td>
                   <td>
                     <button class='btn btn-primary' data-toggle='modal' data-target=#exampleModal-${userElement}>
                       Edit
                    </button>
                                                <!-- Modal Edit -->
                    <div class='modal fade' id=exampleModal-${userElement} tabindex='-1' role='dialog'
                                                     aria-labelledby='exampleModalLabel' aria-hidden='true'>
                                                <div class='modal-dialog' role='document'>
                                                    <div class='modal-content'>
                                                        <div class='modal-header'>
                                                            <h5 class='modal-title' id='exampleModalLabel'>Edit User</h5>
                                                            <button type='button' class='close' data-dismiss='modal'
                                                                    aria-label='Close'>
                                                                <span aria-hidden='true'>&times;</span>
                                                            </button>
                                                        </div>
                <form action=${userElement}/edit method='POST' onsubmit='return editById(${user['id']},this)'>

                                       <div class='modal-body'>
                                                                  <strong>ID</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=` + user['id'] + ` id='idT' name='id' readOnly>
                                                                  <strong>Username</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=` + user['name'] + ` id='nameT' name='name' required>
                                                                  <strong>Login</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=` + user['login'] + ` id='loginT' name='login' required>
                                                                  <strong>Password</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=` + user['password'] + ` id='passwordT' name='password' required>
                                                                  <div class='modal-footer'>
                                                           <button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>
                                                           <button type='submit' class='btn btn-primary'>Edit</button>
                                                     </div>
                                               </div>
                </form>
                                    </div>
                               </div>
                         </div>
                    </td>
                </tr>
                `);
            // console.log(users);
        });

        // $('.table').append(html);
    });
}

function reset() {
    $('.table').html("") ;
}