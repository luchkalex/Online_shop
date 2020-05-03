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
                <a class="nav-link" href="/products/catalog">Catalog</a>
            </li>

            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/control_panel/products">Control panel</a>
                </li>
            </#if>

        </ul>

        <form action="/products/catalog">
            <div class="input-group">
                <label for="title"></label>
                <input type="text" maxlength="255" name="title" id="title" placeholder="Search"
                       class="bg-dark text-white border-dark rounded">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="input-group-append">
                    <button type="submit" class="btn-dark btn"><i class="fas fa-search"></i></button>
                </div>
            </div>
        </form>

        <#if titleError??>
            <div class="text-danger">
                ${titleError}
            </div>
        </#if>

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
        <div class="navbar-text mx-3">
            <#if user?? && user.id??>
                <a class=" link-blue" href="/users/profile">${name}</a>
            <#else>
                Please log in
            </#if>
        </div>
    </div>
</nav>