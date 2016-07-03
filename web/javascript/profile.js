if (document.readyState === 'complete') {
    var follower = document.getElementById("followers")
}

function followersClick() {
    var followers = document.getElementById("followers");
    var following = document.getElementById("following");
    var followers_dashboard = document.getElementById("user-followers-dashboard");
    var following_dashboard = document.getElementById("user-following-dashboard");

    followers.setAttribute("style", "background-color: #90d2de;");
    following.setAttribute("style", "background-color: none");

    followers_dashboard.setAttribute("style", "display: block");
    following_dashboard.setAttribute("style", "display: none");
}

function followingClick() {

    var followers = document.getElementById("followers");
    var following = document.getElementById("following");
    var followers_dashboard = document.getElementById("user-followers-dashboard");
    var following_dashboard = document.getElementById("user-following-dashboard");

    followers.setAttribute("style", "background-color: none");
    following.setAttribute("style", "background-color: #90d2de");

    followers_dashboard.setAttribute("style", "display: none");
    following_dashboard.setAttribute("style", "display: block");
}
