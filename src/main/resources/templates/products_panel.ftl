<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid ml-5">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <h3>Products</h3>

                <a href="/products/add_product" class="btn btn-info ml-auto">
                    <i class="fas fa-plus-circle"></i>
                    <span class="ml-2">New product</span>
                </a>
            </nav>

            <div><#if result??>result</#if></div>

            <div class="line"></div>

            <div>
                <#--TODO: Add feature of discount-->
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
                            <td class="text-center">
                                <input type="text" value="${pf.idMin}" placeholder="min" name="idMin" class="col-sm-5">
                                <input type="text" value="${pf.idMax?string["0"]}" placeholder="max" name="idMax"
                                       class="col-sm-5">
                            </td>
                            <td></td>
                            <td>
                                <input type="text" value="${pf.title}" placeholder="Search name" name="title">
                            </td>

                            <td>
                                <select class="custom-select" id="category_select" name="category">
                                    <option value=''>Chose category</option>
                                    <#list categories as category>
                                        <option value=${category.id}>${category.title}</option>
                                    </#list>
                                </select>
                            </td>
                            <td class="text-center">
                                <input type="text" value="${pf.priceMin}" placeholder="min" name="priceMin"
                                       class="col-sm-5">
                                <input type="text" value="${pf.priceMax?string["0"]}" placeholder="max" name="priceMax"
                                       class="col-sm-5">
                            </td>
                            <td class="text-center">
                                <input type="text" value="${pf.quantityMin}" placeholder="min" name="quantityMin"
                                       class="col-sm-5">
                                <input type="text" value="${pf.quantityMax?string["0"]}" placeholder="max"
                                       name="quantityMax" class="col-sm-5">
                            </td>

                            <td>
                                <select class="custom-select" id="statuses_select" name="productStatus">
                                    <option value='-1'>Chose status</option>
                                    <#list statuses as status>
                                        <option value=${status.id}>${status.title}</option>
                                    </#list>
                                </select>
                            </td>
                            <td>
                                <input type="submit" class="btn btn-info" value="Search">
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list products as product>
                            <tr>
                                <td class="text-center">${product.id}</td>
                                <td><#if product.photo??>
                                        <img src="/img/${product.photo}" alt="Image" class="avatar-img">
                                    <#else >
                                        <img src="/img/default.jpeg" alt="Image" class="avatar-img">
                                    </#if></td>
                                <td>${product.title}</td>
                                <td><#if product.category??>${product.category.title}<#else>none</#if></td>
                                <td class="text-center">${product.price}</td>
                                <td class="text-center">${product.quantity ! 0}</td>
                                <td>${product.productStatus.title}</td>
                                <td>
                                    <a href="/products/edit/${product.id}"><i class="fas fa-edit"></i></a>
                                    <a data-toggle="collapse" href="#quantityInput${product.id}" role="button"
                                       aria-expanded="false" aria-controls="collapseExample"><i
                                                class="fas fa-plus-circle" style="color: green"></i></a>
                                    <a href="/products/delete/${product.id}"><i class="fas fa-minus-circle"
                                                                                style="color: red"></i></a>

                                    <#--FIXME: First product in list haven't form element-->
                                    <div class="collapse" id="quantityInput${product.id}">
                                        <form action="/products/income/${product.id}">
                                            <input type="text" class="form-control-sm col-sm-5" name="quantity">
                                            <input type="submit" class="btn btn-info" value="Add">
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
                </form>
            </div>
        </div>
    </@s.sidebar>
</@c.page>