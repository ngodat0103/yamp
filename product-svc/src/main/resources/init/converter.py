import re


def convert_sql_format(input_file, output_file):
    with open(input_file, 'r', encoding='utf-8') as file:
        sql_content = file.read()

    # Define the new format
    new_format = 'insert into product(created_at,last_modified_at,created_by,last_modified_by,uuid,name,slug_name,category_uuid,image_url,description)'

    # Regular expression to match the old INSERT INTO statements
    old_insert_pattern = re.compile(
        r"INSERT INTO products\((.*?)\) VALUES\n\((.*?)\);",
        re.UNICODE | re.DOTALL | re.MULTILINE
    )

    def convert_match(match):
        columns = match.group(1).split(',')
        values = match.group(2).replace("\n", "").replace("\t", "").split('),')
        element_pattern = re.compile(r"\'(?!\d+\b)(.*?)\'", re.UNICODE | re.DOTALL | re.MULTILINE)
        with open('product-new.sql', 'w', encoding='utf-8') as writter:
            for value in values:
                elements = element_pattern.findall(value)
                if len(elements) == 1:
                    continue
                column_value_dict = dict(zip(columns, value))
                uuid = "uuid_generate_v4()"
                name = elements[0]
                slug_name = elements[1]
                category_uuid = "'7e39d9c0-fc71-449a-a12d-57b4e237f0c8'"  # Assuming category_uuid is not available in the old format

                try:
                    description: str = elements[4]
                except IndexError:
                    description = "''"
                try:
                    image_url = elements[5]
                except IndexError:
                    image_url = "''"
                created_at = "now()"
                last_modified_at = "now()"
                created_by = "'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'"
                last_modified_by = "'f9ef8aa2-260b-4c32-ae25-7c406e83f0b9'"
                image_url =f"'{image_url}'"

                name = name.strip().strip("'")
                name = f"'{name}'"
                slug_name = slug_name.strip().strip("'")
                slug_name = f"'{slug_name}'"
                description = description.strip().strip("'")
                description = f"'{description}'"
                new_insert_statement = f"{new_format} VALUES ({created_at},{last_modified_at},{created_by},{last_modified_by},uuid_generate_v4(),{name},{slug_name},{category_uuid},{image_url},{description});\n"
                writter.write(new_insert_statement)

    re.sub(old_insert_pattern, convert_match, sql_content)


# Example usage
convert_sql_format('product-old.sql', 'product-new.sql')
