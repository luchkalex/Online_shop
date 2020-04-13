<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-group">
        <form method="post">

            <label for="product_title" class="mt-2">Title</label>
            <input type="text" name="title"
                   class="form-control ${(titleError??)?string('is-invalid', '')} my-1 col-sm-4"
                   value="<#if product??>${product.title}</#if>" placeholder="Enter title" id="product_title"/>

            <#if titleError??>
                <div class="invalid-feedback">
                    ${titleError}
                </div>
            </#if>

            <label for="price" class="mt-2">Price</label>
            <input type="text" name="price_value"
                   class="form-control ${(priceError??)?string('is-invalid', '')} my-1 col-sm-4"
                   placeholder="Price" id="price"
                   value="<#if product??>${product.price}</#if>">
            <#if priceError??>
                <div class="invalid-feedback">
                    ${priceError}
                </div>
            </#if>

            <label for="product_desc" class="mt-2">Description</label>
            <input type="text" name="description"
                   class="form-control ${(descriptionError??)?string('is-invalid', '')} my-1 col-sm-4"
                   value="<#if product??>${product.description}</#if>" placeholder="Enter description"
                   id="product_desc"/>

            <#if descriptionError??>
                <div class="invalid-feedback">
                    ${descriptionError}
                </div>
            </#if>

            <label for="release_date" class="mt-2">Release date</label>
            <input type="date" dataformatas="yyyy/mm/dd" name="date"
                   class="form-control ${(release_dateError??)?string('is-invalid', '')} my-1 col-sm-4"
                   placeholder="Enter description" id="release_date"/>

            <#if release_dateError??>
                <div class="invalid-feedback">
                    ${release_dateError}
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary my-1">Save</button>
        </form>
    </div>
</@c.page>