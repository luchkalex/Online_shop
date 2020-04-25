<#include "security.ftl">

<div class="row">
    <#list products as product>
        <div class="col-lg-4 col-md-6 mb-4">
            <div class="card my-2">

                <span style="display: block" class="text-center card-header">${product.productStatus.title}</span>

                <div class="ml-2">
                    <label for="rating"><#if product.rating??>${product.rating}<#else >0</#if></label>
                    <input id="rating" min="1" max="5" value="<#if product.rating??>${product.rating}<#else >0</#if>"
                           class="mx-2 mt-2" type="range" readonly="readonly" disabled>
                </div>

                <a href="/products/${product.id}">
                    <#if product.photo??>
                        <img src="/img/${product.photo}" alt="Image" class="card-img-top product-image">
                    <#else >
                        <img src="/img/default.jpeg" alt="Image" class="card-img-top product-image">
                    </#if>
                </a>


                <div class="m-2">
                    <span style="font-size: 20px">${product.title}</span><br>
                    <#if product.discount?? && product.discount != 0>
                        <a href="#">
                            <strong class="text-muted" style="text-decoration: line-through">${product.price}
                                hrn</strong>
                        </a>
                    </#if>
                    <a href="#"><strong>${product.price - product.discount} hrn</strong></a>
                </div>

                <div class="card-footer text-muted">
                    <#if cur_user??>
                        <#assign inCart = false>
                        <#list cur_user.cartItems as cartItem>
                            <#if cartItem.product.id = product.id><#assign inCart = true ></#if>
                        </#list>
                    </#if>
                    <#if inCart>
                    <#--TODO: Add link to cart page in both cases-->
                        <a href="/users/cart" class="btn btn-secondary">In cart</a>
                    <#else >
                        <a href="/products/add_to_cart/${product.id}" class="btn btn-primary">Add to cart</a>
                    </#if>

                </div>

            </div>
        </div>
        <div>

        </div>
    <#else >
        No products
    </#list>
</div>