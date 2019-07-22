 $( document ).ready( function ( $ )
    {
        var selectedRows = $( 'table.jsgrid-table' ).find( 'tbody' ) // select table body and
            .find( 'tr' ) // select all rows that has
            .has( 'input[type=checkbox]:checked' ) // checked checkbox element
       alert(selectedRows);
    } );