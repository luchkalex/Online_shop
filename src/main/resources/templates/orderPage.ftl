<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container">
        <h3 class="text-center">Order of product</h3>

        <table class="table table-striped table-hover ">
            <tbody>

            <tr>
                <td>Customer</td>
                <td><#if order.user??>${order.user.username}<#else >Unknown</#if></td>
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
                <td>Customer email</td>
                <td><#if order.customerEmail?has_content>${order.customerEmail}<#elseif order.user??>${order.user.email}</#if></td>
            </tr>

            </tbody>
        </table>


        <h5 class="text-center">Products</h5>
        <table class="table table-striped table-hover mt-4">
            <tbody>
            <#list order.orderItems as orderItem>
                <tr>
                    <td>${orderItem.product.title}</td>
                    <td>${orderItem.quantity}
                        (${orderItem.quantity * (orderItem.product.price - orderItem.product.discount)}hrn)
                    </td>
                </tr>
            <#else >
                <tr>
                    <td>No products</td>
                </tr>
            </#list>
            <tr class="bg-success">
                <td>Total</td>
                <td>${order.total}hrn</td>
            </tr>
            </tbody>
        </table>
        <a href="/products/catalog" class="btn btn-primary mt-3">Back to catalog</a>
    </div>
</@c.page>