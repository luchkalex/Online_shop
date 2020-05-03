<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid ml-5">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <h3>Products</h3>

                <a href="/products/add_product_choice_category" class="btn btn-info ml-auto">
                    <i class="fas fa-plus-circle"></i>
                    <span class="ml-2">New product</span>
                </a>
            </nav>

            <div><#if result??>result</#if></div>

            <div class="line"></div>

            <div>

                <form action="/control_panel/products" method="get">
                    <table class="table table-striped table-hover ">
                        <thead>
                        <tr>
                            <th class="text-center">Id</th>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Category</th>
                            <th class="text-center">Price</th>
                            <th class="text-center">Quantity</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                        <tr>
                            <td class="text-center col-sm-1">
                                <div><input type="number" min="0" value="${pf.idMin}" placeholder="min" name="idMin"
                                            class="w-100"></div>
                                <div><input type="number" max="4000000000" value="${pf.idMax?string["0"]}"
                                            placeholder="max" name="idMax"
                                            class="w-100"></div>
                            </td>
                            <td></td>
                            <td>
                                <input type="text" maxlength="255" value="${pf.title}" placeholder="Search name"
                                       name="title" class="w-100">
                            </td>

                            <td>
                                <select class="custom-select" id="category_select" name="category">
                                    <option value='-1'>Category</option>
                                    <#list categories as category>
                                        <option value=${category.id}>${category.title}</option>
                                    </#list>
                                </select>
                            </td>
                            <td class="text-center col-sm-1">
                                <div>
                                    <input type="number" min="0" value="${pf.priceMin}" placeholder="min"
                                           name="priceMin"
                                           class="w-100">
                                </div>
                                <div>
                                    <input type="number" max="300000" value="${pf.priceMax?string["0"]}"
                                           placeholder="max" name="priceMax"
                                           class="w-100">
                                </div>
                            </td>
                            <td class="text-center col-sm-1">
                                <div>
                                    <input type="number" min="0" value="${pf.quantityMin}" placeholder="min"
                                           name="quantityMin"
                                           class="w-100">
                                </div>
                                <div>
                                    <input type="number" max="4000000000" value="${pf.quantityMax?string["0"]}"
                                           placeholder="max"
                                           name="quantityMax" class="w-100">
                                </div>
                            </td>

                            <td>
                                <select class="custom-select" id="statuses_select" name="productStatus">
                                    <option value='-1'>Status</option>
                                    <#list statuses as status>
                                        <option value=${status.id}>${status.title}</option>
                                    </#list>
                                </select>
                            </td>
                            <td>
                                <input type="submit" class="btn btn-info" value="Search">
                            </td>
                        </tr>

                        <tr>
                            <#if idMinError??>
                                <div class="alert alert-danger">
                                    ${idMinError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if idMaxError??>
                                <div class="alert alert-danger">
                                    ${idMaxError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if priceMinError??>
                                <div class="alert alert-danger">
                                    ${priceMinError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if priceMaxError??>
                                <div class="alert alert-danger">
                                    ${priceMaxError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if quantityMinError??>
                                <div class="alert alert-danger">
                                    ${quantityMinError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if quantityMaxError??>
                                <div class="alert alert-danger">
                                    ${quantityMaxError}
                                </div>
                            </#if>
                        </tr>

                        <tr>
                            <#if titleError??>
                                <div class="alert alert-danger">
                                    ${titleError}
                                </div>
                            </#if>
                        </tr>

                        </thead>
                        <tbody>
                        <#list products as product>
                            <#if product.productStatus.title != 'Deleted'>
                                <tr>
                                    <td class="text-center">${product.id}</td>
                                    <td><#if product.photo??>
                                            <img src="/img/${product.photo}" alt="Image" class="avatar-img">
                                        <#else >
                                            <img src="/img/default.jpeg" alt="Image" class="avatar-img">
                                        </#if></td>
                                    <td>${product.title}</td>
                                    <td><#if product.category??>${product.category.title}<#else>none</#if></td>
                                    <td class="text-center">
                                        <#if product.discount?? && product.discount != 0>
                                            <strong class="text-muted"
                                                    style="text-decoration: line-through">${product.price}
                                                hrn</strong>
                                        </#if>
                                        <strong>${product.price - product.discount} hrn</strong>
                                    </td>
                                    <td class="text-center">${product.quantity ! 0}</td>
                                    <td>${product.productStatus.title}</td>
                                    <td>
                                        <a href="/products/${product.id}"><i class="fas fa-eye"
                                                                             style="color: darkblue"></i></a>

                                        <a href="/products/edit/${product.id}"><i class="fas fa-edit"></i></a>
                                        <a data-toggle="collapse" href="#quantityInput${product.id}" role="button"
                                           aria-expanded="false" aria-controls="collapseExample">
                                            <i class="fas fa-plus-circle" style="color: green"></i>
                                        </a>

                                        <a data-toggle="collapse" href="#discountInput${product.id}" role="button"
                                           aria-expanded="false" aria-controls="collapseExample">
                                            <i class="fas fa-percent"></i>
                                        </a>


                                        <a href="/products/delete/${product.id}">
                                            <i class="fas fa-trash-alt" style="color: red"></i>
                                        </a>

                                        <#--FIXME: First product in list haven't form element-->
                                        <div class="collapse" id="quantityInput${product.id}">
                                            <form action="/products/income/${product.id}">
                                                <input type="text" class="form-control-sm w-100" name="quantity">
                                                <input type="submit" class="btn btn-info" value="Add">
                                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            </form>
                                        </div>

                                        <div class="collapse" id="discountInput${product.id}">
                                            <form action="/products/add_discount/${product.id}">
                                                <input type="text" class="form-control-sm w-100" name="discount">
                                                <input type="submit" class="btn btn-info" value="Add">
                                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            </form>
                                        </div>

                                    </td>
                                </tr>
                            </#if>
                        <#else >
                            <tr>
                                <td>No products</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </@s.sidebar>
</@c.page>