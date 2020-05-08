<#import "parts/common.ftl" as c>

<@c.page>
    <div class="container mt-3">
        <h3>Profile</h3>
        <h4>${user.username!"username"}</h4>

        <form method="post" novalidate>
            <div class="form-group">
                <label for="username_input">User name</label>
                <input type="text" maxlength="255" class="form-control col-sm-4" name="username" id="username_input"
                       placeholder="User name" value="${user.username!''}">
            </div>

            <#if usernameError??>
                <div class="alert alert-danger">
                    ${usernameError}
                </div>
            </#if>

            <div class="form-group">
                <label for="password_input">Password</label>
                <input type="password" maxlength="255" class="form-control col-sm-4" name="password" id="password_input"
                       placeholder="Password">
            </div>

            <#if passwordError??>
                <div class="alert alert-danger">
                    ${passwordError}
                </div>
            </#if>

            <div class="form-group">
                <label for="email_input">Email</label>
                <input type="email" maxlength="255" class="form-control col-sm-4" name="email" id="email_input"
                       value="${user.email!''}"
                       placeholder="Email">
            </div>

            <#if emailError??>
                <div class="alert alert-danger">
                    ${emailError}
                </div>
            </#if>

            <div class="form-group">
                <label for="address_input">Address</label>
                <input type="text" maxlength="255" class="form-control col-sm-4" name="address" id="address_input"
                       value="${user.address!''}"
                       placeholder="Address">
            </div>

            <#if addressError??>
                <div class="alert alert-danger">
                    ${addressError}
                </div>
            </#if>


            <div class="form-group">
                <label for="phone_input">Phone</label>
                <input type="tel" maxlength="13" class="form-control col-sm-4" name="phone" id="phone_input"
                       value="${user.phone!''}"
                       placeholder="+(xxx)-xx-xxx-xx-xx">
            </div>

            <#if phoneError??>
                <div class="alert alert-danger">
                    ${phoneError}
                </div>
            </#if>

            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Save</button>
            <a href="/users/deleteAccount" class="btn btn-danger">Delete account</a>
        </form>

        <h3 class="mt-3">Orders</h3>

        <table class="table table-striped table-hover ">
            <thead>
            <tr>
                <th class="text-center">Delivery</th>
                <th class="text-center">Payment</th>
                <th class="text-center">Total</th>
                <th class="text-center">Status</th>
                <th class="text-center">Date</th>
                <th class="text-center">Operations</th>
            </tr>
            </thead>
            <tbody>
            <#list orders as order>

                <tr>
                    <td class="text-center">${order.typesOfDelivery.title}</td>
                    <td class="text-center">${order.typeOfPayment.title}</td>

                    <td class="text-center">${order.total}</td>

                    <td class="text-center">${order.orderStatuses.title!'No status'}</td>

                    <td class="text-center">${order.orderDate!'No date'}</td>

                    <td class="text-center">
                        <a href="/users/order/${order.id}" type="submit" class="btn btn-primary">More</a>
                        <#if (order.orderStatuses.title = "Accepted") || (order.orderStatuses.title = "Awaiting confirmation")>
                            <a href="/users/Canceled/${order.id}" type="submit" class="btn btn-danger">Cancel</a>
                        </#if>
                    </td>
                </tr>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

            <#else >
                <tr>
                    <td>No orders</td>
                </tr>
            </#list>
            </tbody>
        </table>


    </div>
</@c.page>
