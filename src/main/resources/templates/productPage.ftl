<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>

    <div class="container mt-5">

        <div class="row">
            <div class="col">

                <#if product.photo??>
                    <img src="/img/${product.photo}" alt="Image" class="product-big-image">
                <#else >
                    <img src="/img/default.jpeg" alt="Image" class="product-big-image">
                </#if>
            </div>
            <div class="col">
                <h3>${product.title}</h3>

                <div>
                    <label for="rating"><#if product.rating??>${product.rating}<#else >0</#if></label>
                    <i class="fas fa-star" style="color: orange"></i>
                </div>

                <#if product.discount?? && product.discount != 0>
                    <strong class="text-muted" style="text-decoration: line-through">${product.price} hrn</strong>
                </#if>
                <strong style="font-size: 20px">${product.price - product.discount} hrn</strong>

                <span class="ml-3 text-danger">${product.productStatus.title}</span>

                <#if product.description??>
                    <p>${product.description}</p>
                </#if>

                <#if product.category??>
                    <span>Category: ${product.category.title}</span>
                </#if>

                <div class="mt-2">
                    <#if product.productStatus.title = 'Not available'>
                        <div class="btn btn-secondary">Not available</div>
                    <#else >
                        <#assign inCart = false>
                        <#list cartItems as cartItem>
                            <#if cartItem.product.id = product.id><#assign inCart = true ><#break ></#if>
                        </#list>
                        <#if inCart>
                            <a href="/users/cart" class="btn btn-secondary">In cart</a>
                        <#else >
                            <a href="/products/add_to_cart/${product.id}" class="btn btn-primary">Add to cart</a>
                        </#if>
                    </#if>
                </div>

                <#if isAdmin>
                    <div class="card mt-5">
                        <#if product.statistic??>
                            <div class="card-header">Statistic on the product</div>
                            <div class="card-body">
                                <div class="card-text mt-2 border-bottom">Viewed: ${product.statistic.viewed}</div>
                                <div class="card-text mt-2 border-bottom">Sold: ${product.statistic.sold}</div>
                            </div>
                        <#else >
                            <div class="card-header">Statistic is not available</div>
                        </#if>
                    </div>
                </#if>

            </div>
        </div>

        <div class="row mt-3">
            <div class="col">
                <h4>Specifications</h4>
                <div class="m-2">
                    <table class="table table-striped table-hover ">
                        <tbody>
                        <#list product.valuesOfFeatures as valueOfFeature>
                            <tr>
                                <td><span class="text-danger font-weight-bold">${valueOfFeature.feature.title}</span>
                                </td>
                                <td><span>${valueOfFeature.title}</span></td>
                            </tr>
                        <#else>
                            <tr>No specifications</tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col">
                <div>
                    <h3>Comments</h3>

                    <#if (user?? && user.id??) && !commented>
                        <a href="/users/comment/${product.id}" class="btn btn-primary my-2">Give a feedback</a>
                    </#if>

                    <#if product.comments??>
                        <#list product.comments as comment>
                            <div class="card-header font-weight-bold">
                                <span>${comment.author.username}: </span>
                                <label for="rating"><#if comment.rating??>${comment.rating}<#else >0</#if></label>
                                <i class="fas fa-star" style="color: orange"></i>
                            </div>
                            <div class="card-body">
                                ${comment.text}
                            </div>
                        </#list>
                    <#else >
                        No comments
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@c.page>