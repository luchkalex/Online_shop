<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid ml-5 w-50">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <h3>Categories</h3>

                <a href="#new-category" data-toggle="collapse" aria-expanded="false"
                   class="dropdown-toggle btn btn-info ml-auto">
                    <i class="fas fa-plus-circle"></i>
                    <span class="ml-2">New category</span>
                </a>
            </nav>

            <div id="new-category" class="collapse mt-2">
                <form action="/categories/add_category">
                    <label for="category_title" class="mt-2">Title</label>
                    <input type="text" maxlength="45" name="title"
                           class="form-control ${(titleError??)?string('is-invalid', '')} my-1 col-sm-4"
                           id="category_title"/>
                    <input type="submit" class="btn btn-info" value="Create">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>

            <div><#if result??>result</#if></div>

            <div class="line"></div>

            <div>
                <table class="table table-striped table-hover ">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th class="text-center">Quantity of products</th>
                        <th class="text-center">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list categories as category>
                        <tr>
                            <td>${category.title}</td>
                            <td class="text-center">${category.products?size}</td>
                            <td class="text-center col-2">

                                <a data-toggle="collapse" href="#editCategory${category.id}" role="button"
                                   aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-edit"></i>
                                </a>

                                <a data-toggle="collapse" href="#addFeature${category.id}" role="button"
                                   aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-plus-circle" style="color: green"></i>
                                </a>

                                <a data-toggle="collapse" href="#deleteFeature${category.id}" role="button"
                                   aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-minus-circle" style="color: red"></i>
                                </a>

                                <a href="/categories/delete_category/${category.id}">
                                    <i class="fas fa-trash-alt" style="color: red"></i>
                                </a>

                                <div class="collapse" id="editCategory${category.id}">
                                    <form action="/categories/edit_category">
                                        <input type="hidden" value="${category.id}" name="category_id">
                                        <input type="text" maxlength="45" name="title" value="${category.title}">

                                        <input type="submit" class="btn btn-info" value="Save">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    </form>
                                </div>

                                <div class="collapse" id="addFeature${category.id}">
                                    <form action="/categories/add_feature">
                                        <input type="hidden" value="${category.id}" name="category_id">

                                        <select class="custom-select" name="feature_id">
                                            <#list features as feature>
                                                <#assign display = true>
                                                <#list category.featuresOfCategory as foc>
                                                    <#if foc.feature = feature>
                                                        <#assign display = false>
                                                    </#if>
                                                </#list>

                                                <#if display>
                                                    <option value="${feature.id}">${feature.title}</option>
                                                </#if>
                                            </#list>
                                        </select>

                                        <input type="submit" class="btn btn-info" value="Add">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    </form>
                                </div>

                                <div class="collapse" id="deleteFeature${category.id}">
                                    <form action="/categories/delete_feature">
                                        <input type="hidden" value="${category.id}" name="category_id">

                                        <select class="custom-select" name="feature_id">
                                            <#list category.featuresOfCategory as foc>
                                                <option value="${foc.feature.id}">${foc.feature.title}</option>
                                            </#list>
                                        </select>

                                        <input type="submit" class="btn btn-danger" value="Remove">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    <#else >
                        <tr>
                            <td>No products</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>
        </div>
    </@s.sidebar>
</@c.page>