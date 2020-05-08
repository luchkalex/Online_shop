<#macro login path isRegisterForm>

    <form action="${path}" method="post" novalidate>
        <div class="form-group">
            <label for="username_input">User name</label>
            <input type="text" maxlength="255"
                   class="form-control ${(usernameError??)?string('is-invalid', '')} col-sm-4"
                   name="username" id="username_input"
                   value="<#if user?? && user.id??>${user.username}</#if>"
                   placeholder="User name">
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="password_input">Password</label>
            <input type="password" maxlength="255"
                   class="form-control ${(passwordError??)?string('is-invalid', '')} col-sm-4"
                   name="password" id="password_input"
                   placeholder="Password">
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
        <#if isRegisterForm>
            <div class="form-group">
                <label for="conf_password_input">Password</label>
                <input type="password" maxlength="255"
                       class="form-control ${(conf_passwordError??)?string('is-invalid', '')} col-sm-4"
                       name="conf_password" id="conf_password_input"
                       placeholder="Retype password">
                <#if conf_passwordError??>
                    <div class="invalid-feedback">
                        ${conf_passwordError}
                    </div>
                </#if>
            </div>

            <div class="form-group">
                <label for="email_input">Email</label>
                <input type="email" class="form-control ${(emailError??)?string('is-invalid', '')} col-sm-4"
                       name="email" id="email_input"
                       value="<#if user??>${user.email}</#if>"
                       placeholder="Email">
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create user<#else >Sign In</#if></button>
        <div class="mt-3"><#if !isRegisterForm><a href="/registration" class="btn btn-primary my-3">Add new
                user</a></#if></div>
    </form>

</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" class="btn btn-primary " value="Log out"/>
    </form>
</#macro>