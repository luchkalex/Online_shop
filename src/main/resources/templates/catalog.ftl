<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container-1300">
        <div class="row mr-3">
            <div class="col-3 mt-3">
                <form method="post">
                    <input type="submit" class="btn btn-primary" value="Search">
                    <div><span>Price</span></div>
                    <div>
                        <label for="priceMin">From</label>
                        <input type="text" placeholder="min" value="${pf.priceMin}" id="priceMin" name="priceMin"
                               class="col-sm-4">
                        <label for="priceMax">To</label>
                        <input type="text" placeholder="max" value="${pf.priceMax?string["0"]}" id="priceMax"
                               name="priceMax" class="col-sm-4">
                    </div>

                    <#if type?? && type="category">

                        <#list features_of_cat as foc>
                            <div class="mt-3 border-bottom">
                                <span>${foc.feature.title}</span>

                                <#list foc.feature.valuesOfFeature as value_of_feature>
                                    <div class="my-2">
                                        <input type="checkbox" name="features_id"
                                               id="feature_check_box${value_of_feature.id}"
                                               value="${value_of_feature.id}">
                                        <label for="feature_check_box${value_of_feature.id}">${value_of_feature.title}</label>
                                    </div>
                                </#list>

                            </div>
                        </#list>

                    <#else>
                        <div class="mt-3">
                            <span style="font-size: 20px">Categories</span>
                            <#list categories as category>
                                <div class="mt-1 border-right">
                                    <a href="/products/category/${category.id}" class="btn-link">${category.title}</a>
                                </div>
                            </#list>
                        </div>
                    </#if>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
            <div class="col-8">
                <#include "parts/productList.ftl">
            </div>
        </div>
    </div>
</@c.page>