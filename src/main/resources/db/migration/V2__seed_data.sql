-- Insert Roles
INSERT INTO role (id, role_name) VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ROLE_SYSTEM_ADMIN'),
    ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'ROLE_AGENCY_ADMIN'),
    ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'ROLE_AGENT'),
    ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 'ROLE_CLIENT');

-- Insert Permissions for ROLE_SYSTEM_ADMIN
INSERT INTO role_permission (id, permission, role_id) VALUES
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'manage_system', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'manage_agencies', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'manage_users', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'manage_properties', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'view_reports', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'delete_any', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11');

-- Insert Permissions for ROLE_AGENCY_ADMIN
INSERT INTO role_permission (id, permission, role_id) VALUES
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a21', 'manage_agency', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'manage_agents', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a23', 'manage_agency_properties', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a24', 'view_agency_reports', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a25', 'assign_properties', 'b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22');

-- Insert Permissions for ROLE_AGENT
INSERT INTO role_permission (id, permission, role_id) VALUES
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a31', 'agent_create_properties', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a32', 'agent_update_properties', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'agent_view_properties', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a34', 'agent_manage_clients', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a35', 'agent_respond_inquiries', 'c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33');

-- Insert Permissions for ROLE_CLIENT
INSERT INTO role_permission (id, permission, role_id) VALUES
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a41', 'client_view_properties', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a42', 'client_save_favorites', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a43', 'client_contact_agents', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 'client_submit_inquiries', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44'),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a45', 'client_view_profile', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44');
