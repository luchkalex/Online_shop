<#import "parts/common.ftl" as c>
<#import "parts/sidebar.ftl" as s>

<@c.page>
    <@s.sidebar>
        <div class="container-fluid ml-5">

            <nav class="navbar navbar-expand-lg navbar-light bg-light">

                <h3>Features</h3>


                <a href="#new-feature" data-toggle="collapse" aria-expanded="false"
                   class="dropdown-toggle btn btn-info ml-auto">
                    <i class="fas fa-plus-circle"></i>
                    <span class="ml-2">New Feature</span>
                </a>
            </nav>

            <div id="new-feature" class="collapse mt-2">
                <form action="/features/add_feature">
                    <label for="feature_title" class="mt-2">Title</label>
                    <input type="text" name="title"
                           class="form-control ${(titleError??)?string('is-invalid', '')} my-1 col-sm-4"
                           id="feature_title"/>
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
                        <th class="text-center">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list features as feature>
                        <tr>
                            <td>${feature.title}</td>

                            <td class="text-center col-2">

                                <a data-toggle="collapse" href="#editFeature${feature.id}" role="button"
                                   aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-edit"></i>
                                </a>

                                <a data-toggle="collapse" href="#addValue${feature.id}" role="button"
                                   aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-plus-circle" style="color: green"></i>
                                </a>

                                <a data-toggle="collapse" href="#deleteValue${feature.id}" role="button"
                                   aria-expanded="false" aria-controls="collapseExample">
                                    <i class="fas fa-minus-circle" style="color: red"></i>
                                </a>

                                <a href="/features/delete_feature/${feature.id}">
                                    <i class="fas fa-trash-alt" style="color: red"></i>
                                </a>

                                <div class="collapse" id="editFeature${feature.id}">
                                    <form action="/features/edit_feature">
                                        <input type="hidden" value="${feature.id}" name="feature_id">
                                        <input type="text" name="title" value="${feature.title}">

                                        <input type="submit" class="btn btn-info" value="Save">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    </form>
                                </div>

                                <div class="collapse" id="addValue${feature.id}">
                                    <form action="/features/add_value">
                                        <input type="hidden" value="${feature.id}" name="feature_id">

                                        <input type="text" name="value_title">

                                        <input type="submit" class="btn btn-info" value="Add">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    </form>
                                </div>

                                <div class="collapse" id="deleteValue${feature.id}">
                                    <form action="/features/delete_value">
                                        <select class="custom-select" name="value_id">
                                            <#list feature.valuesOfFeature as value>
                                                <option value="${value.id}">${value.title}</option>
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