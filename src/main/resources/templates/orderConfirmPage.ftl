<#import "parts/common.ftl" as c>

<@c.page>
<#--TODO: Maybe move table of product in left side-->
    <div class="container">
        <h3 class="text-center">Order of product</h3>

        <table class="table table-striped table-hover ">
            <tbody>

            <tr>
                <td>Customer</td>
                <td><#if user.username??>${user.username}<#else >Unknown</#if></td>
            </tr>

            <tr>
                <td>Type of payment</td>
                <td>${order.typeOfPayment.title}</td>
            </tr>

            <tr>
                <td>Type of delivery</td>
                <td>${order.typesOfDelivery.title}</td>
            </tr>

            <tr>
                <td>Address shipping</td>
                <td>${order.address}</td>
            </tr>

            <tr>
                <td></td>
                <td></td>
            </tr>

            <tr>
                <td>Products</td>
                <td></td>
            </tr>
            <#list order.orderItems as orderItem>
                <tr>
                    <td class="text-center">${orderItem.product.title}</td>
                    <td class="text-center">${orderItem.quantity} (${orderItem.quantity * orderItem.product.price}hrn)
                    </td>
                </tr>
            <#else >
                <tr>
                    <td>No products</td>
                </tr>
            </#list>
            <tr class="bg-success">
                <td class="text-center">Total sum</td>
                <td class="text-center">${order.total}hrn</td>
            </tr>
            </tbody>
        </table>
        <a href="/products/catalog" class="btn btn-primary mt-3">Back to catalog</a>
    </div>
</@c.page>