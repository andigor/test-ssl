#include "main.h"

#include <openssl/ssl.h>

int main()
{

  SSL_load_error_strings();
  SSL_library_init();

  return 0;
}

