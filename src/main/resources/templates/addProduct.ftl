<#import "parts/common.ftl" as c>

<#--FIXME: Wrong display if title is too long-->

<@c.page>
    <div class="container form-group">
        <form method="post" enctype="multipart/form-data"
              <#if product?? && type??>action="/products/edit/${product.id}"</#if>>

            <label for="product_title" class="mt-2">Title</label>
            <input type="text" name="title"
                   class="form-control my-1 col-sm-4"
                   value="<#if product?? && !titleError??>${product.title}</#if>" placeholder="Enter title"
                   id="product_title"/>

            <#if titleError??>
                <div class="invalid-feedback">
                    ${titleError}
                </div>
            </#if>

            <label for="price" class="mt-2">Price</label>
            <input type="number" max="300000" maxlength="6" min="0" name="price"
                   class="form-control my-1 col-sm-4"
                   placeholder="Price" id="price"
                   value="<#if product?? && !priceError??>${product.price?string["0"]}</#if>">
            <#if priceError??>
                <div class="invalid-feedback">
                    ${priceError}
                </div>
            </#if>

            <label for="product_desc" class="mt-2">Description</label>

            <textarea name="description" id="product_desc" cols="80" rows="10"
                      placeholder="Enter description"><#if product?? && !descriptionError??>${product.description}</#if></textarea>

            <#if descriptionError??>
                <div class="invalid-feedback">
                    ${descriptionError}
                </div>
            </#if>

            <div class="custom-file mt-2 col-sm-4">
                <input type="file" class="custom-file-input form-control"
                       data-url="<#if product?? && product.photo??>/img/${product.photo}</#if>"
                       id="customFile" name="file">

                <label class="custom-file-label" for="customFile">
                    <#if product?? && product.photo??>${product.photo}<#else >Chose file</#if>
                </label>

                <#if product?? && product.photo??>
                    <div>
                        <img src="/img/${product.photo}" alt="Image" class="avatar-img">
                    </div>
                </#if>

            </div>

            <#if photoError??>
                <div class="invalid-feedback">
                    ${photoError}
                </div>
            </#if>

            <#--TODO: Add file error-->

            <input type="hidden" name="productStatus" value="1">

            <h3 class="my-3">Features</h3>

            <#list features_of_cat as foc>
                <div class="mt-3 border-bottom col-sm-4 p-0">

                    <label for="feature${foc.feature.id}Select">${foc.feature.title}</label>

                    <select class="custom-select" id="feature${foc.feature.id}Select" name="features_id">
                        <#list foc.feature.valuesOfFeature as value_of_feature>
                            <option value=${value_of_feature.id}
                                    <#if product??>
                                    <#list product.valuesOfFeatures as products_vof>
                                    <#if value_of_feature.id = products_vof.id>selected</#if>
                                    </#list>
                                    </#if>>
                                ${value_of_feature.title}
                            </option>
                        </#list>
                    </select>
                </div>
            </#list>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if product??><input type="hidden" name="category_id" value="${product.category.id}"/></#if>
            <button type="submit" class="btn btn-primary my-3">Save</button>
        </form>
    </div>
</@c.page>