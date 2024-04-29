
import builtins
import logging
import os
import sys

# We need to include the root directory in sys.path to ensure that we can
# find everything we need when running in the standalone runtime.
root = os.path.dirname(os.path.realpath(__file__))
if sys.path[0] != root:
    sys.path.insert(0, root)

from pgadmin.utils import env, IS_WIN, fs_short_path

# Force log file to rw volume
LOG_FILE = '/var/lib/pgadmin/pgadmin4.log'

# Disable logs through log level
FILE_LOG_LEVEL = 100

WTF_CSRF_HEADERS = ['x-pga-csrftoken']

# Configure OAuth2 authentication through GitHub
AUTHENTICATION_SOURCES = ['oauth2', 'internal']

OAUTH2_AUTO_CREATE_USER = True
OAUTH2_CONFIG = [{
    'OAUTH2_NAME': 'github',
    'OAUTH2_DISPLAY_NAME': 'Github',
    'OAUTH2_CLIENT_ID': env('GITHUB_CLIENT_ID'),
    'OAUTH2_CLIENT_SECRET': env('GITHUB_CLIENT_SECRET'),
    'OAUTH2_TOKEN_URL': '',
    'OAUTH2_AUTHORIZATION_URL': '',
    'OAUTH2_API_BASE_URL': '',
    'OAUTH2_USERINFO_ENDPOINT': 'user',
    'OAUTH2_ICON': 'fa-github',
    'OAUTH2_BUTTON_COLOR': '#3253a8',
}]

# Disable enhanced cookie to avoid CSRF issues
ENHANCED_COOKIE_PROTECTION = False

# Disable browser cache to avoir CSRF issue after logout then login
SEND_FILE_MAX_AGE_DEFAULT = 0