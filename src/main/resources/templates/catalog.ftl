<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container-1300">
        <div class="row mr-3">
            <div class="col-3">
                <#--                <input type="text" value="${pf.priceMin}" placeholder="min" name="priceMin"-->
                <#--                       class="col-sm-5">-->
                <#--                <input type="text" value="${pf.priceMax?string["0"]}" placeholder="max" name="priceMax"-->
                <#--                       class="col-sm-5">-->
                <div><span>Price</span></div>
                <div>
                    <label for="priceMin">From</label>
                    <input type="text" placeholder="min" id="priceMin" name="priceMin" class="col-sm-4">
                    <label for="priceMax">To</label>
                    <input type="text" placeholder="max" id="priceMax" name="priceMax" class="col-sm-4">
                </div>

                <#if type?? && type="category">
                    <#list category.featuresOfCategory as features_of_category>
                        <div>
                            <span>${features_of_category.feature.title}</span>
                            <div>
                                <#list features_of_category.valuesOfFeature as value_of_feature>
                                    <span>${value_of_feature.title}</span>
                                </#list>
                            </div>
                        </div>
                    </#list>

                <#else>
                    <div>
                        <#list categories as category>
                            <div>
                                <a href="/products/category/${category.id}" class="btn-link">${category.title}</a>
                            </div>
                        </#list>
                    </div>
                </#if>

            </div>
            <div class="col-8">
                <#include "parts/productList.ftl">
            </div>
        </div>
    </div>
</@c.page>