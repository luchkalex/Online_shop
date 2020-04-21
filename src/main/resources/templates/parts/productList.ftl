<#include "security.ftl">

<div class="card-columns">
    <#list products as product>
        <div class="card my-2">

            <div><span>Rating <#if product.rating??>${product.rating}</#if> / 5</span></div>

            <#if product.photo??>
                <img src="/img/${product.photo}" alt="Image" class="card-img-top product-image">
            <#else >
                <img src="/img/default.jpeg" alt="Image" class="card-img-top product-image">
            </#if>

            <span>${product.productStatus.title}</span>

            <div class="m-2">
                <span>${product.title}</span><br>
                <#if product.discount?? && product.discount != 0>
                    <a href="#">
                        <strong class="text-muted" style="text-decoration: line-through">${product.price} hrn</strong>
                    </a>
                </#if>
                <a href="#"><strong>${product.price - product.discount} hrn</strong></a>
            </div>

            <div class="m-2">
                <#list product.valuesOfFeatures as valueOfFeature>
                    <div class="border-bottom">
                        <span class="text-danger font-weight-bold">${valueOfFeature.feature.title}</span>
                        <span>${valueOfFeature.title}</span>
                    </div>
                <#else>

                </#list>
            </div>

            <div class="card-footer text-muted">
                <a href="/products/add_to_cart/${product.id}" class="btn btn-primary">Add to cart</a>
            </div>
        </div>

        <div>

        </div>
    <#else >
        No products
    </#list>
</div>