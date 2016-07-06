$( document ).ready(function() {
    if (jQuery('.followers').length > 0) {
        document.getElementById("followers").addEventListener("click", function followersClick() {
            var followers = document.getElementById("followers");
            var following = document.getElementById("following");
            var followers_dashboard = document.getElementById("user-followers-dashboard");
            var following_dashboard = document.getElementById("user-following-dashboard");


            followers.setAttribute("style", "background-color: #90d2de;");
            following.setAttribute("style", "background-color: none");

            followers_dashboard.setAttribute("style", "display: block");
            following_dashboard.setAttribute("style", "display: none");
        });
    }

    if (jQuery('.following').length > 0) {

        document.getElementById("following").addEventListener("click", function followersClick() {

            var followers = document.getElementById("followers");
            var following = document.getElementById("following");
            var followers_dashboard = document.getElementById("user-followers-dashboard");
            var following_dashboard = document.getElementById("user-following-dashboard");

            followers.setAttribute("style", "background-color: none");
            following.setAttribute("style", "background-color: #90d2de");
            // jQuery('#followers')

            followers_dashboard.setAttribute("style", "display: none");
            following_dashboard.setAttribute("style", "display: block");
        });
    }

    $('.search-button').click(function () {
        var inp = $('.users-search').val();
        var url = 'http://localhost:8080/users.jsp?search=' + inp;
        window.location.href = url;
    });
});
