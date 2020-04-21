<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-group">
        <form method="post" enctype="multipart/form-data"
              <#if product?? && type??>action="/products/edit/${product.id}"</#if>>

            <label for="product_title" class="mt-2">Title</label>
            <input type="text" name="title"
                   class="form-control ${(titleError??)?string('is-invalid', '')} my-1 col-sm-4"
                   value="<#if product?? && !titleError??>${product.title}</#if>" placeholder="Enter title"
                   id="product_title"/>

            <#if titleError??>
                <div class="invalid-feedback">
                    ${titleError}
                </div>
            </#if>

            <label for="price" class="mt-2">Price</label>
            <input type="text" name="price"
                   class="form-control ${(priceError??)?string('is-invalid', '')} my-1 col-sm-4"
                   placeholder="Price" id="price"
                   value="<#if product?? && !priceError??>${product.price?string["0"]}</#if>">
            <#if priceError??>
                <div class="invalid-feedback">
                    ${priceError}
                </div>
            </#if>

            <label for="product_desc" class="mt-2">Description</label>
            <input type="text" name="description"
                   class="form-control ${(descriptionError??)?string('is-invalid', '')} my-1 col-sm-4"
                   value="<#if product?? && !descriptionError??>${product.description}</#if>"
                   placeholder="Enter description"
                   id="product_desc"/>

            <#if descriptionError??>
                <div class="invalid-feedback">
                    ${descriptionError}
                </div>
            </#if>

            <label for="release_date" class="mt-2">Release date (optional)</label>
            <input type="date" dataformatas="yyyy/mm/dd" name="release_date"
                   class="form-control ${(release_dateError??)?string('is-invalid', '')} my-1 col-sm-4"
                   placeholder="Enter description" id="release_date"/>

            <#if release_dateError??>
                <div class="invalid-feedback">
                    ${release_dateError}
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
                    <img src="/img/${product.photo}" alt="Image" class="avatar-img">
                </#if>

            </div>

            <#--TODO: Add file error-->

            <input type="hidden" name="productStatus" value="1">

            <div class="col-sm-4 mt-2 p-0">
                <label for="category_select" class="mt-2">Select category</label>
                <select class="custom-select" id="category_select" name="category_id">
                    <#list categories as category>
                        <option value=${category.id}>${category.title}</option>
                    </#list>
                </select>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary my-1">Save</button>
        </form>
    </div>
</@c.page>