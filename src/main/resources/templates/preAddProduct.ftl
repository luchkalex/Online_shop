<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container form-group">
        <form method="get" action="/products/add_product">

            <div class="col-sm-4 mt-2 p-0">
                <label for="category_select" class="mt-2">Select category</label>
                <select class="custom-select" id="category_select" name="category_id">
                    <#list categories as category>
                        <option value=${category.id}>${category.title}</option>
                    </#list>
                </select>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary my-1">Next</button>
        </form>
    </div>
</@c.page>