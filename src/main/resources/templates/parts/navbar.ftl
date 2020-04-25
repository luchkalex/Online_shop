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

            <#if user?? && user.id??>
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

            <li class="nav-item">
                <a class="nav-link" href="/products/catalog">Catalog</a>
            </li>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users">User list</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/products/add_product_choice_category">Add product</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/control_panel/products">Control panel</a>
                </li>
            </#if>

        </ul>
        <li class="nav-item">
            <a class="nav-link" href="/users/cart" style="font-size: 25px; color: orange"><i
                        class="fas fa-shopping-cart"></i></a>
        </li>
        <#if known>
            <@l.logout/>
        <#else>
            <li class="nav-item">
                <a class="nav-link btn btn-primary" href="/login">Login</a>
            </li>
        </#if>
        <div class="navbar-text mx-3"><#if user?? && user.id??>${name}<#else>Please log in</#if></div>
    </div>
</nav>