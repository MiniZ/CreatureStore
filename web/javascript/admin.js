$( document ).ready(function() {
    document.getElementById("users").addEventListener("click", function followersClick() {
        var users = document.getElementById("users");
        var posts = document.getElementById("posts");

        var users_board = document.getElementById("users_board");
        var posts_board = document.getElementById("posts_board");

        users.setAttribute("style", "background-color: #FF2F19;");
        posts.setAttribute("style", "background-color: #DE5B4D");

        users_board.setAttribute("style", "display: block");
        posts_board.setAttribute("style", "display: none");
    });
    document.getElementById("posts").addEventListener("click", function followersClick() {
        var users = document.getElementById("users");
        var posts = document.getElementById("posts");

        var users_board = document.getElementById("users_board");
        var posts_board = document.getElementById("posts_board");

        users.setAttribute("style", "background-color: #DE5B4D;");
        posts.setAttribute("style", "background-color: #FF2F19");

        users_board.setAttribute("style", "display: none");
        posts_board.setAttribute("style", "display: block");
    });

    $('.user-search-button').click(function () {
        var inp = $('.user-search').val();
        window.location.href = 'http://localhost:8080/admin.jsp?post=&user=' + inp;
    });
    $('.post-search-button').click(function () {
        var inp = $('.post-search').val();
        window.location.href = 'http://localhost:8080/admin.jsp?user=&type=post&post=' + inp;
    });

});
