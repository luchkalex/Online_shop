<#include "security.ftl">

<div class="card-columns">
    <#list products as product>
        <div class="card my-2">

            <div class="m-2">
                <span>${product.title}</span><br>
                <i>${product.description}</i>
            </div>

            <div class="card-footer text-muted">
                <a href="#"><strong>${product.price}</strong></a>
            </div>
        </div>

        <div>

        </div>
    <#else >
        No products
    </#list>
</div>