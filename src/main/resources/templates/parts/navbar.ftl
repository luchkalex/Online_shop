<#include "security.ftl">
<#import "login_tmp.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="/">Electro</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>

            <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/users-messages/${currentUserId}">My messages</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/users/profile">Profile</a>
                </li>
            </#if>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users">User list</a>
                </li>
            </#if>

        </ul>
        <#if known>
            <@l.logout/>
        <#else>
            <li class="nav-item">
                <a class="nav-link btn btn-primary" href="/login">Login</a>
            </li>
        </#if>
        <div class="navbar-text mx-3"><#if user??>${name}<#else>Please log in</#if></div>
    </div>
</nav>