<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>


<@c.page>
    <@s.sidebar>
        <div class="content">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <h3>Products</h3>

                <button type="button" id="sidebarCollapse" class="btn btn-info ml-auto">
                    <i class="fa fa-align-justify"></i> <span>toggle sidebar</span>
                </button>
            </nav>


            <div class="line"></div>

            <div>
                <table>
                    <tr>
                        <th>Id</th>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    <#list products as product>
                        <tr>
                            <td>${product.id}</td>
                            <td>Image</td>
                            <td>${product.title}</td>
                            <td>${product.category ! "none"}</td>
                            <td>${product.price}</td>
                            <td>${product.quantity ! 0}</td>
                            <td>Status</td>
                            <td>Actions</td>
                        </tr>
                    <#else >
                        No products
                    </#list>
                </table>
            </div>
        </div>
    </@s.sidebar>
</@c.page>