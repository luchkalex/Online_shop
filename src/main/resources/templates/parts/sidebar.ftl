<#macro sidebar>
    <div class="wrapper sidebar">
        <nav id="sidebar">

            <div class="sidebar-header">
                <h3>CONTROL PANEL</h3>
            </div>


            <ul class="list-unstyled components">
                <#--                <li class="active">-->
                <#--                    <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">Collapsed-->
                <#--                        menu</a>-->
                <#--                    <ul class="collapse list-unstyled" id="">-->
                <#--                        <li>-->
                <#--                            <a href="#">home1</a>-->
                <#--                        </li>-->
                <#--                        <li>-->
                <#--                            <a href="#">home2</a>-->
                <#--                        </li>-->
                <#--                        <li>-->
                <#--                            <a href="#">home3</a>-->
                <#--                        </li>-->
                <#--                    </ul>-->
                <#--                </li>-->

                <li>
                    <a href="/control_panel/products">Products</a>
                </li>

                <li>
                    <a href="/control_panel/categories">Categories</a>
                </li>

                <li>
                    <a href="/control_panel/features">Features</a>
                </li>

                <li>
                    <a href="/control_panel/orders">Orders</a>
                </li>

                <li>
                    <a href="/control_panel/sales_stat">Sales statistic</a>
                </li>


            </ul>
        </nav>

        <#nested>
    </div>
</#macro>